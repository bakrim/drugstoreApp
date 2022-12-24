import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Historique from './historique';
import HistoriqueDetail from './historique-detail';
import HistoriqueUpdate from './historique-update';
import HistoriqueDeleteDialog from './historique-delete-dialog';

const HistoriqueRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Historique />} />
    <Route path="new" element={<HistoriqueUpdate />} />
    <Route path=":id">
      <Route index element={<HistoriqueDetail />} />
      <Route path="edit" element={<HistoriqueUpdate />} />
      <Route path="delete" element={<HistoriqueDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HistoriqueRoutes;
