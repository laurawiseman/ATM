import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, RouterProvider, Route } from 'react-router-dom';

// import { BrowserRouter as Router } from 'react-router-dom';
// import history from './history';

// const router = createBrowserRouter([
//   {
//     path: "/",
//     element: 
//   }
// ])

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    {/* <Router> */}
      <App />
    {/* </Router> */}
  </React.StrictMode>
);

// ReactDOM.render(
//   <div>
//     <Router>
//       <App />
//     </Router>
//   </div>,
//   document.getElementById('root')
// );

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
