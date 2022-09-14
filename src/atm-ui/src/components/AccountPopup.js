import React, { useState } from 'react';
import Popup from 'reactjs-popup';
import NewAccountForm from './NewAccountForm';
import NewTransactionForm from './NewTransactionForm';


const AccountPopup = ({createItem, setCreateItem, item}) => {
    console.log(createItem)

    return (
        <Popup 
            position = "center center"
            defaultOpen
            modal
        >
            {close => (
                <div className="account-container">
                    <button className="close" onClick={() => {
                        setCreateItem(false);
                        close();
                    }}>
                        &times;
                    </button>
                    <div className="header"> 
                        Please enter your desired {item} information
                    </div>
                    {item == "account" 
                        ? <NewAccountForm />
                        : item == "transaction" ? <NewTransactionForm />
                            : null
                    }
                    
                </div>
            )}
        </Popup>
    )
}

export default AccountPopup;