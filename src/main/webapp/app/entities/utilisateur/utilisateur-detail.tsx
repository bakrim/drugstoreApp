import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './utilisateur.reducer';

export const UtilisateurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const utilisateurEntity = useAppSelector(state => state.utilisateur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="utilisateurDetailsHeading">
          <Translate contentKey="drugStoreApp.utilisateur.detail.title">Utilisateur</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="drugStoreApp.utilisateur.email">Email</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.email}</dd>
          <dt>
            <span id="username">
              <Translate contentKey="drugStoreApp.utilisateur.username">Username</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.username}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="drugStoreApp.utilisateur.password">Password</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.password}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="drugStoreApp.utilisateur.active">Active</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="drugStoreApp.utilisateur.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="drugStoreApp.utilisateur.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.prenom}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="drugStoreApp.utilisateur.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.telephone}</dd>
          <dt>
            <Translate contentKey="drugStoreApp.utilisateur.roleList">Role List</Translate>
          </dt>
          <dd>
            {utilisateurEntity.roleLists
              ? utilisateurEntity.roleLists.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {utilisateurEntity.roleLists && i === utilisateurEntity.roleLists.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/utilisateur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/utilisateur/${utilisateurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UtilisateurDetail;
