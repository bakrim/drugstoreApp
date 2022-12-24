import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICommission } from 'app/shared/model/commission.model';
import { getEntities as getCommissions } from 'app/entities/commission/commission.reducer';
import { IRepresentant } from 'app/shared/model/representant.model';
import { getEntity, updateEntity, createEntity, reset } from './representant.reducer';

export const RepresentantUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const commissions = useAppSelector(state => state.commission.entities);
  const representantEntity = useAppSelector(state => state.representant.entity);
  const loading = useAppSelector(state => state.representant.loading);
  const updating = useAppSelector(state => state.representant.updating);
  const updateSuccess = useAppSelector(state => state.representant.updateSuccess);

  const handleClose = () => {
    navigate('/representant');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCommissions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...representantEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...representantEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="drugStoreApp.representant.home.createOrEditLabel" data-cy="RepresentantCreateUpdateHeading">
            <Translate contentKey="drugStoreApp.representant.home.createOrEditLabel">Create or edit a Representant</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="representant-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('drugStoreApp.representant.entite')}
                id="representant-entite"
                name="entite"
                data-cy="entite"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.representant.nom')}
                id="representant-nom"
                name="nom"
                data-cy="nom"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.representant.prenom')}
                id="representant-prenom"
                name="prenom"
                data-cy="prenom"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.representant.telephone')}
                id="representant-telephone"
                name="telephone"
                data-cy="telephone"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.representant.cIN')}
                id="representant-cIN"
                name="cIN"
                data-cy="cIN"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/representant" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default RepresentantUpdate;
