import React, { useState } from 'react';
import Popup from 'reactjs-popup';
import NewAccountForm from './NewAccountForm';


const AccountPopup = ({createAccount, setCreateAccount}) => {
    console.log(createAccount)
    
    return (
        <Popup 
            position = "center center"
            defaultOpen
            modal
        >
            {close => (
                <div className="account-container">
                    <button className="close" onClick={() => {
                        setCreateAccount(false);
                        close();
                    }}>
                        &times;
                    </button>
                    <div className="header"> 
                        Please enter your desired account name 
                    </div>
                    <NewAccountForm />
                </div>
            )}
        </Popup>
    )
}

export default AccountPopup;