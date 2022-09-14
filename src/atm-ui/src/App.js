import './App.css';
import HomeScreen from './screens/HomeScreen';
import AccountScreen from './screens/AccountScreen';
import React from 'react';
// import { BrowserRouter, Route, Routes } from 'react-router-dom';


function App() {
  return (
    <React.Fragment>
      <HomeScreen />
      <AccountScreen id={1} />
    </React.Fragment>


    // <BrowserRouter>
    //   <Routes>
    //     <Route path="/" element={<HomeScreen />} />
    //     <Route path="/home" element={<HomeScreen />} />
    //     <Route path="/account" element={<AccountScreen />} />
    //   </Routes>
    // </BrowserRouter>
  );
}

export default App;
