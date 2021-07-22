import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { LOCAL_STORAGE_KEY } from '../constants';
import { requestGetMyInfo, requestPostLogin } from '../requests';

const loginAsync = createAsyncThunk('auth/login', async (loginData) => {
  try {
    const response = await requestPostLogin(loginData);

    if (!response.ok) {
      throw new Error(response);
    }

    const userData = await response.json();

    localStorage.setItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN, JSON.stringify(userData?.accessToken));

    return userData;
  } catch (error) {
    console.error(error);
  }
});

const getMyInfoAsync = createAsyncThunk('auth/myInfo', async (accessToken) => {
  try {
    const response = await requestGetMyInfo(accessToken);
    if (!response.ok) {
      throw new Error(response);
    }

    const { id, nickname, ageRange, shotVerified, socialProfileUrl } = await response.json();

    return {
      accessToken,
      user: { id, nickname, ageRange, shotVerified, socialProfileUrl },
    };
  } catch (error) {
    console.error(error);
    localStorage.removeItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
  }
});

const authSlice = createSlice({
  name: 'auth',
  initialState: {
    accessToken: '',
    user: {},
  },
  reducers: {
    logout: (state) => {
      state.accessToken = '';
      state.user = {};
    },
  },
  extraReducers: {
    [loginAsync.fulfilled]: (state, action) => {
      state.accessToken = action.payload.accessToken;
      state.user = action.payload.user;
    },
    [getMyInfoAsync.fulfilled]: (state, action) => {
      state.accessToken = action.payload.accessToken;
      state.user = action.payload.user;
    },
    [getMyInfoAsync.rejected]: (state) => {
      state.accessToken = '';
      state.user = {};
    },
  },
});

export const { logout } = authSlice.actions;

export { authSlice, loginAsync, getMyInfoAsync };
