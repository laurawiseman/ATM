import React from 'react';
import { Formik } from 'formik';
import * as Yup from 'yup';
import Form from 'react-bootstrap/Form';
import '../App.css';
import AccountScreen from '../screens/AccountScreen';
// import { useNavigate } from 'react-router-dom';
// import { firebase } from '../firebase';

// const db = firebase.firestore();

const validationSchema = Yup.object().shape({
    accountId: Yup.number()
        .min(1, 'Account Id must be a valid number')
        .integer('Account Id must be a valid number')
        .required('Please enter an id number')
});

const AccountForm = ({createAccount}) => {
    // let navigate = useNavigate()

    async function handleFormSubmit(values) {
        // db.collection('comments').add({
        //     comment: values.anonComment,
        // }).catch((error) => {
        //     console.error('Error adding to firebase: ', error);
        // });
        console.log(values);
        // navigate("/account");

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