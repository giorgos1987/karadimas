import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Statistics from './statistics';
import StatisticsDetail from './statistics-detail';
import StatisticsUpdate from './statistics-update';
import StatisticsDeleteDialog from './statistics-delete-dialog';

const StatisticsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Statistics />} />
    <Route path="new" element={<StatisticsUpdate />} />
    <Route path=":id">
      <Route index element={<StatisticsDetail />} />
      <Route path="edit" element={<StatisticsUpdate />} />
      <Route path="delete" element={<StatisticsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StatisticsRoutes;
