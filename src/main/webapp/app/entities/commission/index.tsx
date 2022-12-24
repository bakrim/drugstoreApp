import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Commission from './commission';
import CommissionDetail from './commission-detail';
import CommissionUpdate from './commission-update';
import CommissionDeleteDialog from './commission-delete-dialog';

const CommissionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Commission />} />
    <Route path="new" element={<CommissionUpdate />} />
    <Route path=":id">
      <Route index element={<CommissionDetail />} />
      <Route path="edit" element={<CommissionUpdate />} />
      <Route path="delete" element={<CommissionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CommissionRoutes;
