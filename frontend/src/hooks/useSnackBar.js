import { useEffect } from 'react';
import ReactDOM from 'react-dom';
import { useDispatch, useSelector } from 'react-redux';
import { SnackBar as SnackBarComponent } from '../components/common';
import {
  openSnackBar as openSnackBarAction,
  closeSnackBar as closeSnackBarAction,
} from '../redux/snackbarSlice';

const useSnackBar = () => {
  const dispatch = useDispatch();
  const { isSnackBarOpen, message } = useSelector((state) => state.snackbarReducer);

  const SnackbarPortal = ({ children }) =>
    ReactDOM.createPortal(children, document.getElementById('snackbar'));

  const SnackBar = () => (
    <SnackbarPortal>
      <SnackBarComponent>{message}</SnackBarComponent>
    </SnackbarPortal>
  );

  const openSnackBar = (message) => {
    dispatch(openSnackBarAction(message));
  };

  useEffect(() => {
    if (!isSnackBarOpen) return;

    const intervalId = setInterval(() => {
      dispatch(closeSnackBarAction());
    }, 3000);

    return () => clearInterval(intervalId);
  }, [isSnackBarOpen]);

  return { isSnackBarOpen, openSnackBar, SnackBar };
};

export default useSnackBar;
