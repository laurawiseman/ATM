import React, { useState } from 'react';

const AccountScreen = ({id}) => {
    console.log(id)
    const [createTransaction, setCreateTransaction] = useState(false)

    // Get account with id # from java backend
    // Show account id, name, balance, list of transactions, and have a button to create transaction 

    const name = "Laura Wiseman"; // account.getName()
    const balance = "$150.00"; // account.getBalance()
    const transactions = [{type: "Deposit", amount: "$100.00"}, {type: "Deposit", amount: "$50.00"}]; // account.getTransactions()

    return (
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
                    {transactions.forEach((transaction) => (
                        <div className='t'>
                            <p>{transaction.type}</p>
                            <p>{transaction.amount}</p>
                        </div>
                    ))}
                </div>
                <button className='button' onClick={() => setCreateTransaction(true)}>Create transaction</button>
            </div>
        </div>
    )
}

export default AccountScreen;