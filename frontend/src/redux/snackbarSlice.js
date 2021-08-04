import { createSlice } from '@reduxjs/toolkit';

const snackbarSlice = createSlice({
  name: 'snackbar',
  initialState: { isSnackBarOpen: false, message: '' },
  reducers: {
    openSnackBar: (state, action) => {
      state.isSnackBarOpen = true;
      state.message = action.payload;
    },
    closeSnackBar: (state) => {
      state.isSnackBarOpen = false;
      state.message = '';
    },
  },
});

export const { openSnackBar, closeSnackBar } = snackbarSlice.actions;

export { snackbarSlice };
