import './App.css';
import HomeScreen from './screens/HomeScreen';
import AccountScreen from './screens/AccountScreen';
import React, { useState } from 'react';


function App() {
  const [account, setAccount] = useState('');

  return (
    <React.Fragment>
      {
      account 
        ? <AccountScreen account={account} setAccount={setAccount} />
        : <HomeScreen account={account} setAccount={setAccount} />
      }
    </React.Fragment>
  );
}

export default App;
