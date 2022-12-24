import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './representant.reducer';

export const RepresentantDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const representantEntity = useAppSelector(state => state.representant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="representantDetailsHeading">
          <Translate contentKey="drugStoreApp.representant.detail.title">Representant</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{representantEntity.id}</dd>
          <dt>
            <span id="entite">
              <Translate contentKey="drugStoreApp.representant.entite">Entite</Translate>
            </span>
          </dt>
          <dd>{representantEntity.entite}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="drugStoreApp.representant.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{representantEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="drugStoreApp.representant.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{representantEntity.prenom}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="drugStoreApp.representant.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{representantEntity.telephone}</dd>
          <dt>
            <span id="cIN">
              <Translate contentKey="drugStoreApp.representant.cIN">C IN</Translate>
            </span>
          </dt>
          <dd>{representantEntity.cIN}</dd>
        </dl>
        <Button tag={Link} to="/representant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/representant/${representantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RepresentantDetail;
