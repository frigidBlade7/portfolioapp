package com.codedevtech.portfolioapp.models;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

public abstract class NavigationCommand {


    public static class NavigationAction extends NavigationCommand {

        @NonNull
        private final NavDirections directions;

        public NavigationAction(@NonNull NavDirections directions) {
            this.directions = directions;
        }

        @NonNull
        public NavDirections getDirections() {
            return directions;
        }

    }


    public static class NavigationId extends NavigationCommand{

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
