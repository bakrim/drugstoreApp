import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/role">
        <Translate contentKey="global.menu.entities.role" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/session">
        <Translate contentKey="global.menu.entities.session" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/historique">
        <Translate contentKey="global.menu.entities.historique" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/utilisateur">
        <Translate contentKey="global.menu.entities.utilisateur" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/representant">
        <Translate contentKey="global.menu.entities.representant" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/dossier-pharmacie">
        <Translate contentKey="global.menu.entities.dossierPharmacie" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/local">
        <Translate contentKey="global.menu.entities.local" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/zone">
        <Translate contentKey="global.menu.entities.zone" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/document">
        <Translate contentKey="global.menu.entities.document" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/commission">
        <Translate contentKey="global.menu.entities.commission" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/etape-validation">
        <Translate contentKey="global.menu.entities.etapeValidation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/dossier-autre">
        <Translate contentKey="global.menu.entities.dossierAutre" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
