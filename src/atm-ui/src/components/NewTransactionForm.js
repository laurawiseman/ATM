import React from 'react';
import { Formik } from 'formik';
import * as Yup from 'yup';
import Form from 'react-bootstrap/Form';
import '../App.css';


const validationSchema = Yup.object().shape({
    type: Yup.string()
        .required('Please enter a transaction type')
        .oneOf(["deposit", "withdrawal"], "Transaction type must be deposit or withdrawal"),
    amount: Yup.number()
        .min(1, 'Transaction amount must be a valid number')
        .required('Please enter an amount number')
});

async function createTransaction(values) {
    const response = await fetch(`/api/transactions`, {
        method: 'POST', 
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(values)
    })
    return await response.json(); 
}

const NewTransactionForm = ({close, account, setCreateTransaction, setNewTransaction}) => {

    async function handleFormSubmit(values) {
        values.id = account.id;

        console.log('new transaction values: ', values);
        // console.log('account balance: ', Number(account.balance.substring(1)));

        createTransaction(values).then(response => {
            console.log('response: ', response);
            setCreateTransaction(false);
            setNewTransaction(true);

            let num = Number(account.balance.substring(1));
            values.type === "deposit" ? num += values.amount : num -= values.amount 
            account.balance = "$" + num;
            console.log('account: ', account)
            close();
        })
    }

    return (
        <div className="form-container">
            <Formik 
                validationSchema={validationSchema}
                onSubmit={(values, {setSubmitting, resetForm}) => {
                    setSubmitting(true);
                    handleFormSubmit(values);
                    setSubmitting(false);
                    resetForm();
                }}
                initialValues={{ type: '', amount: '' }}
            >
                {({
                    handleSubmit,
                    handleChange,
                    handleBlur,
                    isSubmitting,
                    values,
                    touched, 
                    errors
                }) => (
                    <Form onSubmit={handleSubmit} className="form-text form-tr">
                        <Form.Group>
                            <Form.Control 
                                type="text" 
                                name="type" 
                                placeholder="Transaction Type"
                                value={values.type} 
                                onChange={handleChange}
                                onBlur={handleBlur}
                                className={touched.type && errors.type ? "error text" : "text"}
                            />
                            {touched.type && errors.type ? (
                                <div className="error-message">{errors.type}</div>
                                ): null}
                        </Form.Group>
                        <Form.Group>
                            <Form.Control 
                                type="number" 
                                name="amount" 
                                placeholder="Transaction Amount"
                                value={values.amount} 
                                onChange={handleChange}
                                onBlur={handleBlur}
                                className={touched.amount && errors.amount ? "error text" : "text"}
                            />
                            {touched.amount && errors.amount ? (
                                <div className="error-message">{errors.amount}</div>
                                ): null}
                        </Form.Group>
                        <button className="button" type="submit" disabled={isSubmitting || !values.type || errors.type || !values.amount || errors.amount} >
                            Submit
                        </button> 
                    </Form>
                )}
            </Formik>
        </div>
    )
}

export default NewTransactionForm;