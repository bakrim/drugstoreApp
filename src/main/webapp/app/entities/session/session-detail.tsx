import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './session.reducer';

export const SessionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sessionEntity = useAppSelector(state => state.session.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sessionDetailsHeading">
          <Translate contentKey="drugStoreApp.session.detail.title">Session</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sessionEntity.id}</dd>
          <dt>
            <span id="adresseIp">
              <Translate contentKey="drugStoreApp.session.adresseIp">Adresse Ip</Translate>
            </span>
          </dt>
          <dd>{sessionEntity.adresseIp}</dd>
          <dt>
            <span id="dateConnect">
              <Translate contentKey="drugStoreApp.session.dateConnect">Date Connect</Translate>
            </span>
          </dt>
          <dd>
            {sessionEntity.dateConnect ? <TextFormat value={sessionEntity.dateConnect} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="dateDeconnect">
              <Translate contentKey="drugStoreApp.session.dateDeconnect">Date Deconnect</Translate>
            </span>
          </dt>
          <dd>
            {sessionEntity.dateDeconnect ? <TextFormat value={sessionEntity.dateDeconnect} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="drugStoreApp.session.user">User</Translate>
          </dt>
          <dd>{sessionEntity.user ? sessionEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="drugStoreApp.session.utilisateur">Utilisateur</Translate>
          </dt>
          <dd>{sessionEntity.utilisateur ? sessionEntity.utilisateur.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/session" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/session/${sessionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SessionDetail;
