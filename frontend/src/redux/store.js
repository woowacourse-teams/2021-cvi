import { configureStore } from '@reduxjs/toolkit';
import { authSlice } from './authSlice';
import { snackbarSlice } from './snackbarSlice';

const store = configureStore({
  reducer: {
    authReducer: authSlice.reducer,
    snackbarReducer: snackbarSlice.reducer,
  },
});

export default store;
