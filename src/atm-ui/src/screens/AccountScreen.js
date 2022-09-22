import React, { useEffect, useState } from 'react';
import AccountPopup from '../components/AccountPopup';

const AccountScreen = ({account, setAccount}) => {
    const [createTransaction, setCreateTransaction] = useState(false)
    const [transactionsList, setTransactionsList] = useState([])
    const [newTransaction, setNewTransaction] = useState(false)

    async function getTransactions() {
        const response = await fetch(`/api/transactions`)
        return await response.json(); 
    }

    useEffect(() => {
        getTransactions().then(response => {
            let lst = response._embedded.transactionList

            setTransactionsList(lst.filter(transaction => transaction.id === account.id));
            setNewTransaction(false);
        }).catch(err => console.log(err))
        }, [account, newTransaction])

    // Get account with id # from java backend
    // Show account id, name, balance, list of transactions, and have a button to create transaction 
    return (
        <React.Fragment>
        <button className='button' onClick={() => setAccount('')}>Back to Home</button>
        <div className='container'>
            <div className='info'>
                <h1> {account.name} </h1>   
                <div>
                    <h2> Account ID: {account.id} </h2>
                    <h2> Balance: {account.balance} </h2>  
                </div>
            </div>
            <div className='transactions'>
                <div>
                    <h3> Transactions </h3>
                    {transactionsList ? transactionsList.map((transaction, index) => 
                        <div className='t'>
                            <p>{index + 1}</p>
                            <p>{transaction.type}</p>
                            <p>{transaction.amount}</p>
                        </div>
                    ) : <p>No available transactions</p>}
                </div>
                <button className='button' onClick={() => setCreateTransaction(true)}>Create transaction</button>
            </div>
        </div>
        {createTransaction 
            ? <AccountPopup createItem={createTransaction} setCreateItem={setCreateTransaction} item="transaction" account={account} setAccount={setAccount} setNewTransaction={setNewTransaction} />
            : null}
        </React.Fragment>
    )
}

export default AccountScreen;