import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EtapeValidation from './etape-validation';
import EtapeValidationDetail from './etape-validation-detail';
import EtapeValidationUpdate from './etape-validation-update';
import EtapeValidationDeleteDialog from './etape-validation-delete-dialog';

const EtapeValidationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EtapeValidation />} />
    <Route path="new" element={<EtapeValidationUpdate />} />
    <Route path=":id">
      <Route index element={<EtapeValidationDetail />} />
      <Route path="edit" element={<EtapeValidationUpdate />} />
      <Route path="delete" element={<EtapeValidationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EtapeValidationRoutes;
