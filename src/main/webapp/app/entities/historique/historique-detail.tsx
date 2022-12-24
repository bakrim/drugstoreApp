import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './historique.reducer';

export const HistoriqueDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const historiqueEntity = useAppSelector(state => state.historique.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="historiqueDetailsHeading">
          <Translate contentKey="drugStoreApp.historique.detail.title">Historique</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{historiqueEntity.id}</dd>
          <dt>
            <span id="action">
              <Translate contentKey="drugStoreApp.historique.action">Action</Translate>
            </span>
          </dt>
          <dd>{historiqueEntity.action}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="drugStoreApp.historique.date">Date</Translate>
            </span>
          </dt>
          <dd>{historiqueEntity.date ? <TextFormat value={historiqueEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="drugStoreApp.historique.session">Session</Translate>
          </dt>
          <dd>{historiqueEntity.session ? historiqueEntity.session.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/historique" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/historique/${historiqueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HistoriqueDetail;
