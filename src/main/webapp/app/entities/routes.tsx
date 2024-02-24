import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Job from './job';
import JobCategory from './job-category';
import CustomerDetails from './customer-details';
import Cart from './cart';
import Supplierpayments from './supplierpayments';
import Booking from './booking';
import Supplier from './supplier';
import Customerpayments from './customerpayments';
import Statistics from './statistics';
import CompanyProfile from './company-profile';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="job/*" element={<Job />} />
        <Route path="job-category/*" element={<JobCategory />} />
        <Route path="customer-details/*" element={<CustomerDetails />} />
        <Route path="cart/*" element={<Cart />} />
        <Route path="supplierpayments/*" element={<Supplierpayments />} />
        <Route path="booking/*" element={<Booking />} />
        <Route path="supplier/*" element={<Supplier />} />
        <Route path="customerpayments/*" element={<Customerpayments />} />
        <Route path="statistics/*" element={<Statistics />} />
        <Route path="company-profile/*" element={<CompanyProfile />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
