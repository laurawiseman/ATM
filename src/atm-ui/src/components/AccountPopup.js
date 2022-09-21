import React from 'react';
import Popup from 'reactjs-popup';
import NewAccountForm from './NewAccountForm';
import NewTransactionForm from './NewTransactionForm';


const AccountPopup = ({createItem, setCreateItem, item, account, setAccount, setNewTransaction}) => {
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
                    {item === "account" 
                        ? <NewAccountForm setAccount={setAccount} />
                        : item === "transaction" ? <NewTransactionForm close={close} account={account} setCreateTransaction={setCreateItem} setNewTransaction={setNewTransaction} />
                            : null
                    }
                    
                </div>
            )}
        </Popup>
    )
}

export default AccountPopup;