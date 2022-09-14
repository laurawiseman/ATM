import React, { useState } from 'react';
import AccountForm from '../components/AccountForm';
import Popup from 'reactjs-popup';
import NewAccountForm from '../components/NewAccountForm';
import AccountPopup from '../components/AccountPopup';


const HomeScreen = () => {
    const [createAccount, setCreateAccount] = useState(false);

    return (
        <React.Fragment>
        <div className="App">
            <header className="App-header">
                <h1>
                    Welcome to our ATM Application
                </h1>
            </header>
            <p>
                Please create an account or enter your account ID to get started
            </p>
            <button className='button' onClick={() => setCreateAccount(true)}>Create account</button>
            <AccountForm createAccount={createAccount} />
        </div>
        {createAccount 
            ? <AccountPopup createItem={createAccount} setCreateItem={setCreateAccount} item="account" />
            : null}
        </React.Fragment>
    )
}

export default HomeScreen;