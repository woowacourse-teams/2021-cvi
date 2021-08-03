import ReactDOM from 'react-dom';
import PropTypes from 'prop-types';
import { SnackBar as SnackBarComponent } from '../components/common';
import { useEffect, useState } from 'react';

const useSnackBar = () => {
  const [isSnackBarOpen, setIsSnackbarOpen] = useState(false);

  const SnackbarPortal = ({ children }) =>
    ReactDOM.createPortal(children, document.getElementById('snackbar'));

  const SnackBar = ({ children }) => (
    <SnackbarPortal>
      <SnackBarComponent>{children}</SnackBarComponent>
    </SnackbarPortal>
  );

  const openSnackBar = () => setIsSnackbarOpen(true);

  useEffect(() => {
    if (!isSnackBarOpen) return;

    const intervalId = setInterval(() => {
      setIsSnackbarOpen(false);
    }, 3000);

    return () => clearInterval(intervalId);
  }, [isSnackBarOpen]);

  SnackBar.propTypes = {
    children: PropTypes.string.isRequired,
  };

  return { isSnackBarOpen, openSnackBar, SnackBar };
};

export default useSnackBar;
