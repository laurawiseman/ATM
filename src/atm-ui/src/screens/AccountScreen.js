import React, { useState } from 'react';
import AccountPopup from '../components/AccountPopup';

const AccountScreen = ({id}) => {
    console.log(id)
    const [createTransaction, setCreateTransaction] = useState(false)

    // Get account with id # from java backend
    // Show account id, name, balance, list of transactions, and have a button to create transaction 

    const name = "Laura Wiseman"; // account.getName()
    const balance = "$150.00"; // account.getBalance()
    const transactions = [{num: 1, id: 1, type: "Deposit", amount: "$100.00"}, {num: 2, id: 1, type: "Deposit", amount: "$50.00"}]; // account.getTransactions()

    return (
        <React.Fragment>
        <div className='container'>
            <div className='info'>
                <h1> {name} </h1>   
                <div>
                    <h2> Account ID: {id} </h2>
                    <h2> Balance: {balance} </h2>  
                </div>
            </div>
            <div className='transactions'>
                <div>
                    <h3> Transactions </h3>
                    {transactions.map((transaction, index) => 
                        <div className='t'>
                            <p>{transaction.num}</p>
                            <p>{transaction.type}</p>
                            <p>{transaction.amount}</p>
                        </div>
                    )}
                </div>
                <button className='button' onClick={() => setCreateTransaction(true)}>Create transaction</button>
            </div>
        </div>
        {createTransaction 
            ? <AccountPopup createItem={createTransaction} setCreateItem={setCreateTransaction} item="transaction" />
            : null}
        </React.Fragment>
    )
}

export default AccountScreen;