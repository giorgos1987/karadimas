import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CompanyProfile from './company-profile';
import CompanyProfileDetail from './company-profile-detail';
import CompanyProfileUpdate from './company-profile-update';
import CompanyProfileDeleteDialog from './company-profile-delete-dialog';

const CompanyProfileRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CompanyProfile />} />
    <Route path="new" element={<CompanyProfileUpdate />} />
    <Route path=":id">
      <Route index element={<CompanyProfileDetail />} />
      <Route path="edit" element={<CompanyProfileUpdate />} />
      <Route path="delete" element={<CompanyProfileDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CompanyProfileRoutes;
