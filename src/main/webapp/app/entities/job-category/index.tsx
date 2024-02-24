import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import JobCategory from './job-category';
import JobCategoryDetail from './job-category-detail';
import JobCategoryUpdate from './job-category-update';
import JobCategoryDeleteDialog from './job-category-delete-dialog';

const JobCategoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<JobCategory />} />
    <Route path="new" element={<JobCategoryUpdate />} />
    <Route path=":id">
      <Route index element={<JobCategoryDetail />} />
      <Route path="edit" element={<JobCategoryUpdate />} />
      <Route path="delete" element={<JobCategoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JobCategoryRoutes;
