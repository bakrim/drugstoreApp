import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './local.reducer';

export const LocalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const localEntity = useAppSelector(state => state.local.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="localDetailsHeading">
          <Translate contentKey="drugStoreApp.local.detail.title">Local</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{localEntity.id}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="drugStoreApp.local.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{localEntity.longitude}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="drugStoreApp.local.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{localEntity.latitude}</dd>
          <dt>
            <span id="adresse">
              <Translate contentKey="drugStoreApp.local.adresse">Adresse</Translate>
            </span>
          </dt>
          <dd>{localEntity.adresse}</dd>
          <dt>
            <Translate contentKey="drugStoreApp.local.zone">Zone</Translate>
          </dt>
          <dd>{localEntity.zone ? localEntity.zone.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/local" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/local/${localEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocalDetail;
