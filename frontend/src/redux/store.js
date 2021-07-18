import { configureStore } from '@reduxjs/toolkit';
import { authSlice } from './authSlice';

const store = configureStore({
  reducer: {
    authReducer: authSlice.reducer,
  },
});

export default store;
