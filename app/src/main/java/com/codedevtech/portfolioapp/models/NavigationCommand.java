package com.codedevtech.portfolioapp.models;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

public class NavigationCommand {


    public class NavigationAction extends NavigationCommand {

        @NonNull
        private final NavDirections directions;

        public NavigationAction(@NonNull NavDirections directions) {
            this.directions = directions;
        }

        @NonNull
        public NavDirections getDirections() {
            return directions;
        }

/*        @NonNull
        public final NavigationCommand.NavigationAction copy(@NonNull NavDirections directions) {
            return new NavigationCommand.NavigationAction(directions);
        }*/
    }


    public class NavigationId{

        @NonNull
        private final int navigationId;

        public NavigationId(int navigationId) {
            this.navigationId = navigationId;
        }

        public int getNavigationId() {
            return navigationId;
        }
    }
}
