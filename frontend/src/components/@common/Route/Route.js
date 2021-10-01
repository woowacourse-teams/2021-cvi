import PropTypes from 'prop-types';
import { Redirect, Route } from 'react-router-dom';
import { LOCAL_STORAGE_KEY, PATH } from '../../../constants';

const isLogin = () => JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN));

const PublicRoute = ({ component: Component, restricted, ...rest }) => (
  <Route
    {...rest}
    render={(props) =>
      isLogin() && restricted ? <Redirect to={PATH.HOME} /> : <Component {...props} />
    }
  />
);

const PrivateRoute = ({ render: renderComponent, ...rest }) => (
  <Route {...rest} render={() => (isLogin() ? renderComponent() : <Redirect to={PATH.LOGIN} />)} />
);

PublicRoute.propTypes = {
  component: PropTypes.elementType,
  restricted: PropTypes.bool,
};

PrivateRoute.propTypes = {
  render: PropTypes.func,
  restricted: PropTypes.bool,
};

PublicRoute.defaultProps = {
  component: null,
  restricted: false,
};

PrivateRoute.defaultProps = {
  render: () => {},
  restricted: false,
};

export { PublicRoute, PrivateRoute };
