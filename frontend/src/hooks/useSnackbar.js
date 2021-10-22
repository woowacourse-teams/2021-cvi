import { useEffect } from 'react';
import ReactDOM from 'react-dom';
import { useDispatch, useSelector } from 'react-redux';
import { Snackbar as SnackbarComponent } from '../components/@common';
import {
  openSnackbar as openSnackbarAction,
  closeSnackbar as closeSnackbarAction,
} from '../redux/snackbarSlice';

const useSnackbar = () => {
  const dispatch = useDispatch();
  const { isSnackbarOpen, message } = useSelector((state) => state.snackbarReducer);

  const SnackbarPortal = ({ children }) =>
    ReactDOM.createPortal(children, document.getElementById('snackbar'));

  const Snackbar = () => (
    <SnackbarPortal>
      <SnackbarComponent>{message}</SnackbarComponent>
    </SnackbarPortal>
  );

  const openSnackbar = (message) => {
    dispatch(openSnackbarAction(message));
  };

  useEffect(() => {
    if (!isSnackbarOpen) return;

    const timerId = setTimeout(() => {
      dispatch(closeSnackbarAction());
    }, 3000);

    return () => clearTimeout(timerId);
  }, [isSnackbarOpen]);

  return { isSnackbarOpen, openSnackbar, Snackbar };
};

export default useSnackbar;
