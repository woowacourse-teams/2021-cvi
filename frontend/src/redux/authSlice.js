import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { LOCAL_STORAGE_KEY } from '../constants';
import { fetchGetMyInfo } from '../service/fetch';

const getMyInfoAsync = createAsyncThunk('auth/myInfo', async (accessToken) => {
  try {
    const response = await fetchGetMyInfo(accessToken);

    if (!response.ok) {
      throw new Error(response);
    }

    const { id, nickname, ageRange, socialId, socialProvider, socialProfileUrl } =
      await response.json();

    return {
      accessToken,
      user: { id, nickname, ageRange, socialId, socialProvider, socialProfileUrl },
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

export { authSlice, getMyInfoAsync };
