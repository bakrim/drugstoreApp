import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Representant from './representant';
import RepresentantDetail from './representant-detail';
import RepresentantUpdate from './representant-update';
import RepresentantDeleteDialog from './representant-delete-dialog';

const RepresentantRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Representant />} />
    <Route path="new" element={<RepresentantUpdate />} />
    <Route path=":id">
      <Route index element={<RepresentantDetail />} />
      <Route path="edit" element={<RepresentantUpdate />} />
      <Route path="delete" element={<RepresentantDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RepresentantRoutes;
