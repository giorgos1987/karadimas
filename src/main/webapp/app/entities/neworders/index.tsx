import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Neworders from './neworders';
import NewordersDetail from './neworders-detail';
import NewordersUpdate from './neworders-update';
import NewordersDeleteDialog from './neworders-delete-dialog';

const NewordersRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Neworders />} />
    <Route path="new" element={<NewordersUpdate />} />
    <Route path=":id">
      <Route index element={<NewordersDetail />} />
      <Route path="edit" element={<NewordersUpdate />} />
      <Route path="delete" element={<NewordersDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NewordersRoutes;
