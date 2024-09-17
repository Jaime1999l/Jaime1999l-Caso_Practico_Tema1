import React, { useContext } from 'react';
import { BrowserRouter as Router, Route, Redirect, Switch } from 'react-router-dom';
import Login from './components/Auth/Login';
import Register from './components/Auth/Register';
import AdminDashboard from './components/Dashboard/AdminDashboard';
import UserDashboard from './components/Dashboard/UserDashboard';
import { AuthContext } from './context/AuthContext';

function App() {
    const { auth } = useContext(AuthContext);

    const PrivateRoute = ({ component: Component, ...rest }) => (
        <Route
            {...rest}
            render={(props) =>
                auth.token ? <Component {...props} /> : <Redirect to="/login" />
            }
        />
    );

    return (
        <Router>
            <Switch>
                <Route path="/login" component={Login} />
                <Route path="/register" component={Register} />
                <PrivateRoute path="/admin" component={AdminDashboard} />
                <PrivateRoute path="/user" component={UserDashboard} />
                <Redirect from="/" to="/login" />
            </Switch>
        </Router>
    );
}

export default App;