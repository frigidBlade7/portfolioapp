package com.codedevtech.portfolioapp.models;

import com.google.android.material.snackbar.Snackbar;

public abstract class SnackbarCommand {

    public static class SnackbarString extends SnackbarCommand{

        private String snackbarString;

        public SnackbarString(String snackbarString) {
            this.snackbarString = snackbarString;
        }

        public String getSnackbarString() {
            return snackbarString;
        }
    }

    public static class SnackbarId extends SnackbarCommand{
        private int snackbarId;

        public SnackbarId(int snackbarId) {
            this.snackbarId = snackbarId;
        }

        public int getSnackbarId() {
            return snackbarId;
        }
    }
}
