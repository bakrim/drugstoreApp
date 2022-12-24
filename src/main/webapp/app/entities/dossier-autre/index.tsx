import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DossierAutre from './dossier-autre';
import DossierAutreDetail from './dossier-autre-detail';
import DossierAutreUpdate from './dossier-autre-update';
import DossierAutreDeleteDialog from './dossier-autre-delete-dialog';

const DossierAutreRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DossierAutre />} />
    <Route path="new" element={<DossierAutreUpdate />} />
    <Route path=":id">
      <Route index element={<DossierAutreDetail />} />
      <Route path="edit" element={<DossierAutreUpdate />} />
      <Route path="delete" element={<DossierAutreDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DossierAutreRoutes;
