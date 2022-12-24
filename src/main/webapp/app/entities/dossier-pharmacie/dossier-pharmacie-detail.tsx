import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './dossier-pharmacie.reducer';

export const DossierPharmacieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const dossierPharmacieEntity = useAppSelector(state => state.dossierPharmacie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dossierPharmacieDetailsHeading">
          <Translate contentKey="drugStoreApp.dossierPharmacie.detail.title">DossierPharmacie</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{dossierPharmacieEntity.id}</dd>
          <dt>
            <span id="numero">
              <Translate contentKey="drugStoreApp.dossierPharmacie.numero">Numero</Translate>
            </span>
          </dt>
          <dd>{dossierPharmacieEntity.numero}</dd>
          <dt>
            <span id="dateDepot">
              <Translate contentKey="drugStoreApp.dossierPharmacie.dateDepot">Date Depot</Translate>
            </span>
          </dt>
          <dd>
            {dossierPharmacieEntity.dateDepot ? (
              <TextFormat value={dossierPharmacieEntity.dateDepot} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="nature">
              <Translate contentKey="drugStoreApp.dossierPharmacie.nature">Nature</Translate>
            </span>
          </dt>
          <dd>{dossierPharmacieEntity.nature}</dd>
          <dt>
            <span id="statut">
              <Translate contentKey="drugStoreApp.dossierPharmacie.statut">Statut</Translate>
            </span>
          </dt>
          <dd>{dossierPharmacieEntity.statut}</dd>
          <dt>
            <span id="dateDerniereModif">
              <Translate contentKey="drugStoreApp.dossierPharmacie.dateDerniereModif">Date Derniere Modif</Translate>
            </span>
          </dt>
          <dd>
            {dossierPharmacieEntity.dateDerniereModif ? (
              <TextFormat value={dossierPharmacieEntity.dateDerniereModif} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="commentaire">
              <Translate contentKey="drugStoreApp.dossierPharmacie.commentaire">Commentaire</Translate>
            </span>
          </dt>
          <dd>{dossierPharmacieEntity.commentaire}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="drugStoreApp.dossierPharmacie.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{dossierPharmacieEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="drugStoreApp.dossierPharmacie.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{dossierPharmacieEntity.prenom}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="drugStoreApp.dossierPharmacie.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{dossierPharmacieEntity.telephone}</dd>
          <dt>
            <span id="cIN">
              <Translate contentKey="drugStoreApp.dossierPharmacie.cIN">C IN</Translate>
            </span>
          </dt>
          <dd>{dossierPharmacieEntity.cIN}</dd>
          <dt>
            <Translate contentKey="drugStoreApp.dossierPharmacie.user">User</Translate>
          </dt>
          <dd>{dossierPharmacieEntity.user ? dossierPharmacieEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="drugStoreApp.dossierPharmacie.documentList">Document List</Translate>
          </dt>
          <dd>
            {dossierPharmacieEntity.documentLists
              ? dossierPharmacieEntity.documentLists.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {dossierPharmacieEntity.documentLists && i === dossierPharmacieEntity.documentLists.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="drugStoreApp.dossierPharmacie.utilisateur">Utilisateur</Translate>
          </dt>
          <dd>{dossierPharmacieEntity.utilisateur ? dossierPharmacieEntity.utilisateur.id : ''}</dd>
          <dt>
            <Translate contentKey="drugStoreApp.dossierPharmacie.local">Local</Translate>
          </dt>
          <dd>{dossierPharmacieEntity.local ? dossierPharmacieEntity.local.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/dossier-pharmacie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/dossier-pharmacie/${dossierPharmacieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DossierPharmacieDetail;
