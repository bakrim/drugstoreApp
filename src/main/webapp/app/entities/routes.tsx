import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Role from './role';
import Session from './session';
import Historique from './historique';
import Utilisateur from './utilisateur';
import Representant from './representant';
import DossierPharmacie from './dossier-pharmacie';
import Local from './local';
import Zone from './zone';
import Document from './document';
import Commission from './commission';
import EtapeValidation from './etape-validation';
import DossierAutre from './dossier-autre';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="role/*" element={<Role />} />
        <Route path="session/*" element={<Session />} />
        <Route path="historique/*" element={<Historique />} />
        <Route path="utilisateur/*" element={<Utilisateur />} />
        <Route path="representant/*" element={<Representant />} />
        <Route path="dossier-pharmacie/*" element={<DossierPharmacie />} />
        <Route path="local/*" element={<Local />} />
        <Route path="zone/*" element={<Zone />} />
        <Route path="document/*" element={<Document />} />
        <Route path="commission/*" element={<Commission />} />
        <Route path="etape-validation/*" element={<EtapeValidation />} />
        <Route path="dossier-autre/*" element={<DossierAutre />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
