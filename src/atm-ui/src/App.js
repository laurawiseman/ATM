import './App.css';
import HomeScreen from './screens/HomeScreen';
import AccountScreen from './screens/AccountScreen';
import React from 'react';

function App() {
  return (
    <React.Fragment>
    <HomeScreen />
    <AccountScreen id={1} />
    </React.Fragment>
  );
}

export default App;
