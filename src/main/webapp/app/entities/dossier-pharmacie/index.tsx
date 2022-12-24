import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DossierPharmacie from './dossier-pharmacie';
import DossierPharmacieDetail from './dossier-pharmacie-detail';
import DossierPharmacieUpdate from './dossier-pharmacie-update';
import DossierPharmacieDeleteDialog from './dossier-pharmacie-delete-dialog';

const DossierPharmacieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DossierPharmacie />} />
    <Route path="new" element={<DossierPharmacieUpdate />} />
    <Route path=":id">
      <Route index element={<DossierPharmacieDetail />} />
      <Route path="edit" element={<DossierPharmacieUpdate />} />
      <Route path="delete" element={<DossierPharmacieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DossierPharmacieRoutes;
