import role from 'app/entities/role/role.reducer';
import session from 'app/entities/session/session.reducer';
import historique from 'app/entities/historique/historique.reducer';
import utilisateur from 'app/entities/utilisateur/utilisateur.reducer';
import representant from 'app/entities/representant/representant.reducer';
import dossierPharmacie from 'app/entities/dossier-pharmacie/dossier-pharmacie.reducer';
import local from 'app/entities/local/local.reducer';
import zone from 'app/entities/zone/zone.reducer';
import document from 'app/entities/document/document.reducer';
import commission from 'app/entities/commission/commission.reducer';
import etapeValidation from 'app/entities/etape-validation/etape-validation.reducer';
import dossierAutre from 'app/entities/dossier-autre/dossier-autre.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  role,
  session,
  historique,
  utilisateur,
  representant,
  dossierPharmacie,
  local,
  zone,
  document,
  commission,
  etapeValidation,
  dossierAutre,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
