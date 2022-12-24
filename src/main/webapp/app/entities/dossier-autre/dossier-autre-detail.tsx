import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './dossier-autre.reducer';

export const DossierAutreDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dossierAutreEntity = useAppSelector(state => state.dossierAutre.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dossierAutreDetailsHeading">
          <Translate contentKey="drugStoreApp.dossierAutre.detail.title">DossierAutre</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.id}</dd>
          <dt>
            <span id="numero">
              <Translate contentKey="drugStoreApp.dossierAutre.numero">Numero</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.numero}</dd>
          <dt>
            <span id="numeroEnvoi">
              <Translate contentKey="drugStoreApp.dossierAutre.numeroEnvoi">Numero Envoi</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.numeroEnvoi}</dd>
          <dt>
            <span id="dateDepot">
              <Translate contentKey="drugStoreApp.dossierAutre.dateDepot">Date Depot</Translate>
            </span>
          </dt>
          <dd>
            {dossierAutreEntity.dateDepot ? <TextFormat value={dossierAutreEntity.dateDepot} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dateEnvoi">
              <Translate contentKey="drugStoreApp.dossierAutre.dateEnvoi">Date Envoi</Translate>
            </span>
          </dt>
          <dd>
            {dossierAutreEntity.dateEnvoi ? <TextFormat value={dossierAutreEntity.dateEnvoi} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="typeDemendeur">
              <Translate contentKey="drugStoreApp.dossierAutre.typeDemendeur">Type Demendeur</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.typeDemendeur}</dd>
          <dt>
            <span id="profession">
              <Translate contentKey="drugStoreApp.dossierAutre.profession">Profession</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.profession}</dd>
          <dt>
            <span id="nature">
              <Translate contentKey="drugStoreApp.dossierAutre.nature">Nature</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.nature}</dd>
          <dt>
            <span id="statut">
              <Translate contentKey="drugStoreApp.dossierAutre.statut">Statut</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.statut}</dd>
          <dt>
            <span id="dateDerniereModif">
              <Translate contentKey="drugStoreApp.dossierAutre.dateDerniereModif">Date Derniere Modif</Translate>
            </span>
          </dt>
          <dd>
            {dossierAutreEntity.dateDerniereModif ? (
              <TextFormat value={dossierAutreEntity.dateDerniereModif} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="commentaire">
              <Translate contentKey="drugStoreApp.dossierAutre.commentaire">Commentaire</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.commentaire}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="drugStoreApp.dossierAutre.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="drugStoreApp.dossierAutre.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.prenom}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="drugStoreApp.dossierAutre.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.telephone}</dd>
          <dt>
            <span id="cIN">
              <Translate contentKey="drugStoreApp.dossierAutre.cIN">C IN</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.cIN}</dd>
          <dt>
            <span id="raisonSocial">
              <Translate contentKey="drugStoreApp.dossierAutre.raisonSocial">Raison Social</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.raisonSocial}</dd>
          <dt>
            <span id="iCE">
              <Translate contentKey="drugStoreApp.dossierAutre.iCE">I CE</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.iCE}</dd>
          <dt>
            <span id="numRC">
              <Translate contentKey="drugStoreApp.dossierAutre.numRC">Num RC</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.numRC}</dd>
          <dt>
            <span id="iF">
              <Translate contentKey="drugStoreApp.dossierAutre.iF">I F</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.iF}</dd>
          <dt>
            <span id="numAffiliation">
              <Translate contentKey="drugStoreApp.dossierAutre.numAffiliation">Num Affiliation</Translate>
            </span>
          </dt>
          <dd>{dossierAutreEntity.numAffiliation}</dd>
          <dt>
            <Translate contentKey="drugStoreApp.dossierAutre.user">User</Translate>
          </dt>
          <dd>{dossierAutreEntity.user ? dossierAutreEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="drugStoreApp.dossierAutre.documentList">Document List</Translate>
          </dt>
          <dd>
            {dossierAutreEntity.documentLists
              ? dossierAutreEntity.documentLists.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {dossierAutreEntity.documentLists && i === dossierAutreEntity.documentLists.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="drugStoreApp.dossierAutre.utilisateur">Utilisateur</Translate>
          </dt>
          <dd>{dossierAutreEntity.utilisateur ? dossierAutreEntity.utilisateur.id : ''}</dd>
          <dt>
            <Translate contentKey="drugStoreApp.dossierAutre.local">Local</Translate>
          </dt>
          <dd>{dossierAutreEntity.local ? dossierAutreEntity.local.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/dossier-autre" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/dossier-autre/${dossierAutreEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DossierAutreDetail;
