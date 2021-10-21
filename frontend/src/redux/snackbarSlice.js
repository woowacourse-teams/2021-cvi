import { createSlice } from '@reduxjs/toolkit';

const snackbarSlice = createSlice({
  name: 'snackbar',
  initialState: { isSnackbarOpen: false, message: '' },
  reducers: {
    openSnackbar: (state, action) => {
      state.isSnackbarOpen = true;
      state.message = action.payload;
    },
    closeSnackbar: (state) => {
      state.isSnackbarOpen = false;
      state.message = '';
    },
  },
});

export const { openSnackbar, closeSnackbar } = snackbarSlice.actions;

export { snackbarSlice };
