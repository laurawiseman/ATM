import React from 'react';
import { Formik } from 'formik';
import * as Yup from 'yup';
import Form from 'react-bootstrap/Form';
import '../App.css';

const validationSchema = Yup.object().shape({
    accountId: Yup.number()
        .min(1, 'Account Id must be a valid number')
        .integer('Account Id must be a valid number')
        .required('Please enter an id number')
});

async function getAccount(id) {
    const response = await fetch(`/api/accounts/${id}`)
    return await response.json(); 
}

const AccountForm = ({createAccount, setAccount}) => {

    async function handleFormSubmit(values) {
        let id = values.accountId; 

        getAccount(id).then(response => {
            setAccount(response)
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
                initialValues={{ accountId: '' }}
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
                    <Form onSubmit={handleSubmit} className="form-text">
                        <Form.Group className="id-number">
                            <Form.Control 
                                type="number" 
                                name="accountId" 
                                placeholder="Account Id"
                                value={values.accountId} 
                                onChange={handleChange}
                                onBlur={handleBlur}
                                className={touched.accountId && errors.accountId && !createAccount ? "error id" : "id"}
                            />
                            {touched.accountId && errors.accountId && !createAccount ? (
                                <div className="error-message">{errors.accountId}</div>
                                ): null}
                        </Form.Group>
                        <button className="button" type="submit" disabled={isSubmitting || !values.accountId || errors.accountId} >
                            Submit
                        </button> 
                    </Form>
                )}
            </Formik>
        </div>
    )
}

export default AccountForm;