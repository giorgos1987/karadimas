import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Customerpayments from './customerpayments';
import CustomerpaymentsDetail from './customerpayments-detail';
import CustomerpaymentsUpdate from './customerpayments-update';
import CustomerpaymentsDeleteDialog from './customerpayments-delete-dialog';

const CustomerpaymentsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Customerpayments />} />
    <Route path="new" element={<CustomerpaymentsUpdate />} />
    <Route path=":id">
      <Route index element={<CustomerpaymentsDetail />} />
      <Route path="edit" element={<CustomerpaymentsUpdate />} />
      <Route path="delete" element={<CustomerpaymentsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CustomerpaymentsRoutes;
