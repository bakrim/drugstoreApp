import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './commission.reducer';

export const CommissionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const commissionEntity = useAppSelector(state => state.commission.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="commissionDetailsHeading">
          <Translate contentKey="drugStoreApp.commission.detail.title">Commission</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{commissionEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="drugStoreApp.commission.date">Date</Translate>
            </span>
          </dt>
          <dd>{commissionEntity.date ? <TextFormat value={commissionEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="decision">
              <Translate contentKey="drugStoreApp.commission.decision">Decision</Translate>
            </span>
          </dt>
          <dd>{commissionEntity.decision}</dd>
          <dt>
            <span id="motif">
              <Translate contentKey="drugStoreApp.commission.motif">Motif</Translate>
            </span>
          </dt>
          <dd>{commissionEntity.motif}</dd>
          <dt>
            <Translate contentKey="drugStoreApp.commission.representantList">Representant List</Translate>
          </dt>
          <dd>
            {commissionEntity.representantLists
              ? commissionEntity.representantLists.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {commissionEntity.representantLists && i === commissionEntity.representantLists.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="drugStoreApp.commission.dossierPharmacie">Dossier Pharmacie</Translate>
          </dt>
          <dd>{commissionEntity.dossierPharmacie ? commissionEntity.dossierPharmacie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/commission" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/commission/${commissionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CommissionDetail;
