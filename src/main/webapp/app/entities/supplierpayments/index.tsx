import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Supplierpayments from './supplierpayments';
import SupplierpaymentsDetail from './supplierpayments-detail';
import SupplierpaymentsUpdate from './supplierpayments-update';
import SupplierpaymentsDeleteDialog from './supplierpayments-delete-dialog';

const SupplierpaymentsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Supplierpayments />} />
    <Route path="new" element={<SupplierpaymentsUpdate />} />
    <Route path=":id">
      <Route index element={<SupplierpaymentsDetail />} />
      <Route path="edit" element={<SupplierpaymentsUpdate />} />
      <Route path="delete" element={<SupplierpaymentsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SupplierpaymentsRoutes;
