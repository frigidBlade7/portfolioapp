//grant access to firebase functions 
const functions = require('firebase-functions');
//grant admin access to enable CRUD ops on locations not related to the trigger event
const admin = require('firebase-admin');
//initialise
admin.initializeApp();
//access the db
const db = admin.firestore();
const rtdb = admin.database();
const num_shards = 5;

//u

exports.newMessage = functions.database
    .ref('messages/{chatroomId}/{messageId}')
    .onWrite((change, context)=>{

        const chatroomId = context.params.chatroomId;
        const messageId = context.params.messageId;

        //rtdb.ref('chats').child(chatroomId)

        return rtdb.ref('messages').child(chatroomId).child(messageId).on("value", function(snapshot) {
            //console.log(snapshot.val());
            const data = snapshot.val();

            return rtdb.ref('chats').child(chatroomId).set({
                displayName:  data.sender,
                timestamp: data.timestamp,
                message:data.message
            });

          }, function (errorObject) {
            console.log("The read failed: " + errorObject.code);
          });
    });



exports.createNewUser = functions.firestore
    .document('/users/{userId}')
    .onCreate((change, context)=>{
        
        const userId = context.params.userId;
        
        createCounter(db.collection('following_count').doc(userId), num_shards);

        createCounter(db.collection('follower_count').doc(userId), num_shards);

        setWelcomeMessage(rtdb.ref('messages').child(userId), "Hello and welcome to Folio");


        //for posts createCounter(db.collection('like_count').doc(userId), num_shards);

        return;

    });


exports.updateUserFollowing = functions.firestore
    .document('/following/{userId}/followingIds/{targetId}')
    .onCreate((change, context)=>{
        const newValue = change.data();

        const userId = context.params.userId;
        const targetId = context.params.targetId;

        //console.log(targetId);
        //console.log(userId);


        return db.collection('users').doc(targetId).get().then(snapshot =>{
            const user = snapshot.data();

            //return console.log(user);

            // db.collection('followers').doc(targetId).collection('followersIds').doc(userId)
            // .set({
            //     followingId:targetId,
            //     followingDisplayName: user.firstName+" "+user.lastName,
            //     followingDisplayPhoto: user.displayPhoto
            // },{merge: true});


            const ref = db.collection('followers').doc(targetId).collection('followersIds').doc(userId);
            ref.set({
                id: targetId,
                displayName: user.firstName+ " "+ user.lastName,
                //displayPhoto: user.photoUrl
            }, {merge: true}).catch(error=>{
                console.log(error);
            });
            

            //return incrementCounter(ref, num_shards);
            const increment = admin.firestore.FieldValue.increment(1);
        
            return db.collection('users').doc(targetId).update({
                followerCount: increment
            }).catch(error=>{
                console.log(error);
            });

        }).catch(error=>{
            console.log(error);
        });

    

    });

exports.deleteUserFollowing = functions.firestore
    .document('/following/{userId}/followingIds/{targetId}')
    .onDelete((change, context)=>{

        const userId = context.params.userId;
        const targetId = context.params.targetId;

        db.collection('followers').doc(targetId).collection('followersIds').doc(userId)
            .delete();

            
        const decrement = admin.firestore.FieldValue.increment(-1);
        
        return db.collection('users').doc(targetId).update({
            followerCount: decrement
        }).catch(error=>{
            console.log(error);
        });     


    });

    exports.newChatroom = functions.firestore
    .document('chatrooms/{chatroomId}')
    .onCreate((change, context)=>{
        const chatroomId = context.params.chatroomId;




        
        // const promises = [];

        // change.forEach(doc=>{
        //     const data = doc.data();
        //     promises.push(data.id)
        // });

    });

//update user document when new photo is uploaded or details are changed
exports.updateUserDetails = functions.firestore
    .document('/users/{userId}')
    .onUpdate((change, context) =>{
        const newValue = change.after.data();
        const oldValue = change.before.data();
        const userId = context.params.userId;
        
        if (newValue === oldValue) return null;

        //display name change
        if (newValue.firstName !== oldValue.firstName || newValue.lastName !== oldValue.lastName){
            db.collection('feed').doc(userId).collection('posts')
                .where('userId', '=', userId).get().then(snapshot =>{
                    const promises = [];

                    snapshot.forEach(doc=>{
                        promises.push(doc.ref.update({
                            displayName:newValue.firstName+ " "+newValue.lastName
                        }));
                    });

                    return Promise.all(promises)
                }).catch(error=>{
                    console.log(error);
                });

            // db.collection('followers').get().then(snapshot=>{
            //     const promises = [];

            //     snapshot.forEach(doc=>{
            //         promises.push(doc)
            //     })
            // })
        }

        if (newValue.photoUrl !== oldValue.photoUrl){
            db.collection('feed').doc(userId).collection('posts')
                .where('userId', '=', userId).get().then(snapshot =>{
                    const promises = [];

                    snapshot.forEach(doc=>{
                        promises.push(doc.ref.update({
                            displayPhoto:newValue.photoUrl
                        }));
                    });

                    return Promise.all(promises)
                }).catch(error=>{
                    console.log(error);
                });

            db.collection('following').doc(userId).collection('followingIds')
            .doc(newValue.id).update({
                displayPhoto:newValue.photoUrl
            });
        }

        return;
    });




// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

function createCounter(ref, num_shards) {
    var batch = db.batch();

    // Initialize the counter document
    batch.set(ref, { num_shards: num_shards });

    // Initialize each shard with count=0
    for (let i = 0; i < num_shards; i++) {
        let shardRef = ref.collection('shards').doc(i.toString());
        batch.set(shardRef, { count: 0 });
    }

    // Commit the write batch
    return batch.commit();
}

function incrementCounter(ref, num_shards) {
    // Select a shard of the counter at random
    const shard_id = Math.floor(Math.random() * num_shards).toString();
    const shard_ref = ref.collection('shards').doc(shard_id);

    // Update count
    return shard_ref.update("count", firebase.firestore.FieldValue.increment(1));
}


function setWelcomeMessage(rtdbRef, message){

    rtdbRef.push().set({
        //chatroomId: is value of push()
        message:message,
        sender:'Folio Support',
        timestamp : admin.database.ServerValue.TIMESTAMP
    });

    return;
}