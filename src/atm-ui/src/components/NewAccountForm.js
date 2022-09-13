import React from 'react';
import { Formik } from 'formik';
import * as Yup from 'yup';
import Form from 'react-bootstrap/Form';
import '../App.css';
// import AccountScreen from '../screens/AccountScreen';
// import { firebase } from '../firebase';

// const db = firebase.firestore();

const validationSchema = Yup.object().shape({
    name: Yup.string()
        .min(3, 'Account name must have at least 3 characters')
        .required('Please enter a name for your account')
});

const NewAccountForm = () => {

    async function handleFormSubmit(values) {
        // db.collection('comments').add({
        //     comment: values.anonComment,
        // }).catch((error) => {
        //     console.error('Error adding to firebase: ', error);
        // });
        // return <AccountScreen />
        console.log(values);
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
                initialValues={{ name: '' }}
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
                        <Form.Group>
                            <Form.Control 
                                type="text" 
                                name="name" 
                                placeholder="Account Name"
                                value={values.name} 
                                onChange={handleChange}
                                onBlur={handleBlur}
                                className={touched.name && errors.name ? "error text" : "text"}
                            />
                            {touched.name && errors.name ? (
                                <div className="error-message">{errors.name}</div>
                                ): null}
                        </Form.Group>
                        <button className="button" type="submit" disabled={isSubmitting || !values.name || errors.name} >
                            Submit
                        </button> 
                    </Form>
                )}
            </Formik>
        </div>
    )
}

export default NewAccountForm;