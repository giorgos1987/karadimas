import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Cart from './cart';
import Customers from './customers';
import Job from './job';
import Neworders from './neworders';
import Customerpayments from './customerpayments';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="cart/*" element={<Cart />} />
        <Route path="customers/*" element={<Customers />} />
        <Route path="job/*" element={<Job />} />
        <Route path="neworders/*" element={<Neworders />} />
        <Route path="customerpayments/*" element={<Customerpayments />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
