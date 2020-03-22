//grant access to firebase functions 
const functions = require('firebase-functions');
//grant admin access to enable CRUD ops on locations not related to the trigger event
const admin = require('firebase-admin');
//initialise
admin.initializeApp();
//access the db
const db = admin.firestore();


//update user document when new photo is uploaded
exports.updateUserDownloadUrl = functions.firestore
    .document('/users/{userId}')
    .onWrite((change, context) =>{
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
        }

        return;
    });




// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
